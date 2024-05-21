package com.example.stocki.utility

object HtmlTemplates {
    fun getYouTubeVideoFrame(videoUrl: String): String {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <style>
                    body { margin: 0; padding: 0; }
                    iframe { position: absolute; top: 0; left: 0; width: 100%; height: 100%; }
                </style>
            </head>
            <body>
                <iframe src="$videoUrl" frameborder="0" allowfullscreen></iframe>
            </body>
            </html>
        """.trimIndent()
    }
}