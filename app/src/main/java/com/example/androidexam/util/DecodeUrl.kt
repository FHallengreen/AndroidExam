package com.example.androidexam.util

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun decodeUrl(encodedString: String): String {
    return URLDecoder.decode(encodedString, StandardCharsets.UTF_8.toString())
}