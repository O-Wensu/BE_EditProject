package edit.edit.controller;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.member.LoginRequestDto;
import edit.edit.dto.member.SignupRequestDto;
import edit.edit.jwt.JwtUtil;
import edit.edit.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @GetMapping
    public ResponseDto memberInfo(HttpServletRequest httpServletRequest) {
        return memberService.memberInfo(httpServletRequest);
    }
}
