package edit.edit.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobEnum job;

    @OneToOne(mappedBy = "member")
    @JsonManagedReference
    private Profile profile;

    //TODO 프로필 추가

    @Builder
    public Member(String userId, String password, String email, String nickname, JobEnum job) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.job = job;
    }
}
