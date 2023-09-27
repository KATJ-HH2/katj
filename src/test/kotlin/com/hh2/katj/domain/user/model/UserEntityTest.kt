package com.hh2.katj.domain.user.model

import com.hh2.katj.domain.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDateTime

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class UserEntityTest (
    private val userRepository: UserRepository
) {

    @Test
    fun `등록일자와 수정일자는 자동입력`() {
        val user: User = User(
            email = "jeff3877@naver.com",
            phoneNumber = "01032535576",
            userName = "탁지성",
            gender = Gender.MALE,
        )
        val save = userRepository.save(user)
        assertThat(save.createdAt).isBefore(LocalDateTime.now())
        assertThat(save.updatedAt).isBefore(LocalDateTime.now())
    }
}