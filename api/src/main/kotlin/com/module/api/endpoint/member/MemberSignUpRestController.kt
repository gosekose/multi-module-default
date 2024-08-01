package com.module.api.endpoint.member

import com.module.api.response.CustomResponse
import com.module.member.model.PhoneNumber
import com.module.member.port.inport.MemberSignUpUseCase
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class MemberSignUpRestController(
    private val memberSignUpUseCase: MemberSignUpUseCase,
) {
    @PostMapping("/api/v1/signup")
    fun signUp(@RequestBody request: SignUpHttpRequest): ResponseEntity<CustomResponse.SuccessResponse<Unit?>> {
        memberSignUpUseCase.signUp(request.toCommand())

        val location = URI(CREATED_LOCATION)
        val headers = HttpHeaders()
        headers.location = location

        return ResponseEntity(
            CustomResponse.SuccessResponse.of(
                status = HttpStatus.CREATED,
                data = null,
            ),
            headers,
            HttpStatus.CREATED
        )
    }

    data class SignUpHttpRequest(
        val phoneNumber: String,
        val password: String,
    ) {
        fun toCommand(): MemberSignUpUseCase.SignUpCommand {
            return MemberSignUpUseCase.SignUpCommand(
                phoneNumber = PhoneNumber(phoneNumber),
                password = password,
            )
        }
    }

    companion object {
        private const val CREATED_LOCATION = "/api/v1/login"
    }
}