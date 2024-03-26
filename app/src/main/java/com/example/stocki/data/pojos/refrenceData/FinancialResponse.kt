package com.example.stocki.data.pojos.refrenceData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FinancialsResponse(
    val count: Int,
    val next_url: String?,
    val request_id: String,
    val results: List<Financials>,
    val status: String
): Parcelable
@Parcelize
data class Financials(
    val cik: String,
    val company_name: String,
    val end_date: String,
    val filing_date: String,
    val financials: FinancialDetails,
    val fiscal_period: String,
    val fiscal_year: String,
    val source_filing_file_url: String,
    val source_filing_url: String,
    val start_date: String
): Parcelable
@Parcelize
data class FinancialDetails(
    val balance_sheet: BalanceSheet,
    val cash_flow_statement: CashFlowStatement,
    val comprehensive_income: ComprehensiveIncome,
    val income_statement: IncomeStatement
): Parcelable
@Parcelize
data class BalanceSheet(
    val assets: FinancialItem,
    val current_assets: FinancialItem,
    val current_liabilities: FinancialItem,
    val equity: FinancialItem,
    val equity_attributable_to_noncontrolling_interest: FinancialItem,
    val equity_attributable_to_parent: FinancialItem,
    val liabilities: FinancialItem,
    val liabilities_and_equity: FinancialItem,
    val noncurrent_assets: FinancialItem,
    val noncurrent_liabilities: FinancialItem
): Parcelable
@Parcelize
data class CashFlowStatement(
    val exchange_gains_losses: FinancialItem,
    val net_cash_flow: FinancialItem,
    val net_cash_flow_continuing: FinancialItem,
    val net_cash_flow_from_financing_activities: FinancialItem,
    val net_cash_flow_from_financing_activities_continuing: FinancialItem,
    val net_cash_flow_from_investing_activities: FinancialItem,
    val net_cash_flow_from_investing_activities_continuing: FinancialItem,
    val net_cash_flow_from_operating_activities: FinancialItem,
    val net_cash_flow_from_operating_activities_continuing: FinancialItem
): Parcelable
@Parcelize
data class ComprehensiveIncome(
    val comprehensive_income_loss: FinancialItem,
    val comprehensive_income_loss_attributable_to_noncontrolling_interest: FinancialItem,
    val comprehensive_income_loss_attributable_to_parent: FinancialItem,
    val other_comprehensive_income_loss: FinancialItem,
    val other_comprehensive_income_loss_attributable_to_parent: FinancialItem
): Parcelable
@Parcelize
data class IncomeStatement(
    val basic_earnings_per_share: FinancialItem,
    val benefits_costs_expenses: FinancialItem,
    val cost_of_revenue: FinancialItem,
    val costs_and_expenses: FinancialItem,
    val diluted_earnings_per_share: FinancialItem,
    val gross_profit: FinancialItem,
    val income_loss_from_continuing_operations_after_tax: FinancialItem,
    val income_loss_from_continuing_operations_before_tax: FinancialItem,
    val income_tax_expense_benefit: FinancialItem,
    val interest_expense_operating: FinancialItem,
    val net_income_loss: FinancialItem,
    val net_income_loss_attributable_to_noncontrolling_interest: FinancialItem,
    val net_income_loss_attributable_to_parent: FinancialItem,
    val net_income_loss_available_to_common_stockholders_basic: FinancialItem,
    val operating_expenses: FinancialItem,
    val operating_income_loss: FinancialItem,
    val participating_securities_distributed_and_undistributed_earnings_loss_basic: FinancialItem,
    val preferred_stock_dividends_and_other_adjustments: FinancialItem,
    val revenues: FinancialItem
): Parcelable

@Parcelize
data class FinancialItem(
    val label: String,
    val order: Int,
    val unit: String,
    val value: Double
): Parcelable