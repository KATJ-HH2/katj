package com.hh2.katj

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SampleCode {
    @Test
    fun `일 더하기 일은 이다`() {
        //given
        val number1 = 1
        val number2 = 1

        // when, then
        assertEquals(2, number1+number2)
    }
}