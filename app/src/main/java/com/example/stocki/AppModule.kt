package com.example.stocki

import android.app.Application
import android.content.Context
import com.example.stocki.account.signin.SigninViewModel
import com.example.stocki.account.signup.SignupUseCase
import com.example.stocki.account.signup.SignupViewModel
import com.example.stocki.account.signup.SignupWithGoogleUseCase
import com.example.stocki.data.firebase.FirebaseManager
import com.example.stocki.data.localDatabase.ConcreteLocalSource
import com.example.stocki.data.localDatabase.localSource
import com.example.stocki.data.remoteDatabase.RemoteSource
import com.example.stocki.data.remoteDatabase.StockiClient
import com.example.stocki.data.repository.Repository
import com.example.stocki.data.repository.UserRepository
import com.example.stocki.data.sharedpreferences.SharedPreference
import com.example.stocki.data.sharedpreferences.SharedPreferences
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // or the appropriate component
object ContextModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context.applicationContext
    }

    @Provides
    @Singleton
    fun provideFirebaseManager(@ApplicationContext context: Context , sharedPreference: SharedPreference): FirebaseManager {
        return FirebaseManager(context,sharedPreference)
    }
    @Provides
    fun provideRemoteSource(): RemoteSource {
        return StockiClient() // Replace RemoteSourceImpl with your actual implementation
    }
    @Provides
    fun provideLocalSource(@ApplicationContext context: Context): localSource {
        return ConcreteLocalSource(context) // Replace RemoteSourceImpl with your actual implementation
    }
    @Provides
    fun provideRepository(
        remoteSource: RemoteSource,
        localSource: localSource
    ): Repository {
        return Repository(remoteSource, localSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firebaseManager: FirebaseManager): UserRepository {
        return UserRepository(firebaseManager)
    }
    @Provides
    fun provideSharedPreferencesWrapper(context: Context): SharedPreference {
        return SharedPreferences(context)
    }
}
/*
@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    @ActivityScoped
    fun provideSignupViewModel(
        userRepository: UserRepository
    ): SignupViewModel {
        return SignupViewModel(userRepository)
    }
*/
    @Module
    @InstallIn(ActivityRetainedComponent::class) // This determines the scope of the provided dependencies
    object ViewModelModule {

        @Provides
        @ActivityRetainedScoped // or any other suitable scope
        fun provideSignupViewModel(signupUseCase: SignupUseCase,signupWithGoogleUseCase: SignupWithGoogleUseCase): SignupViewModel {
            return SignupViewModel(signupUseCase,signupWithGoogleUseCase)
        }
   /* @Provides
    @ActivityRetainedScoped // or any other suitable scope
    fun provideSignipViewModel(userRepository: UserRepository): SigninViewModel {
        return SigninViewModel(userRepository)
    }*/
    }
/*
@Singleton
@Component(modules = [ContextModule::class, ViewModelModule::class])
interface AppComponent : AndroidInjector<MyApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    // Component methods
    override fun inject(application: MyApp)
}
*/


    /* @Provides
     @ActivityScoped
     fun provideUserRepository(
         firebaseManager: FirebaseManager
     ): UserRepository {
         return UserRepository(firebaseManager)
     }*/




/*
@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    @ActivityScoped
    fun provideSignupViewModel(
        userRepository: UserRepository
    ): SignupViewModel {
        return SignupViewModel(userRepository)
    }
}
*/
/*
@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(firebaseManager: FirebaseManager): UserRepository {
        return UserRepository(firebaseManager)
    }
}
*/
