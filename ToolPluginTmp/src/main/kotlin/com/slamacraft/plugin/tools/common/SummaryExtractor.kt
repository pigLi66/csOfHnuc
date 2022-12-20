package com.slamacraft.plugin.redpack.common

import com.hankcs.hanlp.HanLP
import java.math.BigDecimal

object SummaryExtractor {
    private val stopWords: MutableList<String> = ArrayList()
        get() {
            if (field.isNotEmpty()) {
                return field
            }
            val words = javaClass.getResourceAsStream("StopWords.txt")
                ?.reader()
                ?.readLines() ?: field
            field.addAll(words)
            return field
        }

    fun getSummary(text: String, summarySize: Int = 30): String {
        val summary = HanLP.extractSummary(text, summarySize)
        return summary.distinct().joinToString(",") { it }
    }

    fun filterTitle(text: String, titleList: List<String>): String {
        // 计算词频
        val segment = HanLP.newSegment()

        // 词频表
        val tfMap = segment.seg(text)
            .groupBy { it.word }
            .filter { !stopWords.contains(it.key) }
            .mapValues { it -> it.value.size }

        var title = ""
        var maxScope = BigDecimal.ZERO

        titleList.groupBy { it }.forEach {
            val words = segment.seg(it.key)
            val scope = BigDecimal(it.value.size) * words.sumOf { word ->
                BigDecimal(tfMap.getOrDefault(word.word, 0) / words.size)
            }
            if (scope > maxScope) {
                maxScope = scope
                title = it.key
            }
        }

        return title
    }
}
