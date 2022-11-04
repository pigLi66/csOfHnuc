package org.dahanjsy.autoredpack.common

import com.hankcs.hanlp.HanLP

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
        var maxScope = 0

        titleList.forEach {
            val words = segment.seg(it)
            val scope = words.sumOf { word ->
                tfMap.getOrDefault(word.word, 0)
            }
            if (scope > maxScope) {
                maxScope = scope
                title = it
            }
        }

        return title
    }
}
