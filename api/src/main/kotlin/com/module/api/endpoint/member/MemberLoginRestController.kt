package com.module.api.endpoint.member

import com.module.api.response.CustomResponse
import com.module.member.model.PhoneNumber
import com.module.member.port.inport.MemberLoginUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberLoginRestController(
    private val memberLoginUseCase: MemberLoginUseCase,
) {
    @PostMapping("/api/v1/login")
    fun login(@RequestBody request: LoginHttpRequest): ResponseEntity<CustomResponse.SuccessResponse<String>> {
        val token = memberLoginUseCase.login(request.toCommand())

        return ResponseEntity(
            CustomResponse.SuccessResponse.of(
                status = HttpStatus.OK,
                data = token.accessToken,
            ),
            HttpStatus.OK
        )
    }

    data class LoginHttpRequest(
        val phoneNumber: String,
        val password: String,
    ) {
        fun toCommand(): MemberLoginUseCase.LoginCommand {
            return MemberLoginUseCase.LoginCommand(
                phoneNumber = PhoneNumber(phoneNumber),
                password = password,
            )
        }
    }
}