package edit.edit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joinChatRoom_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    private String roomName;

    public JoinChatRoom(Member member, ChatRoom chatRoom, String partnerName) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.roomName = partnerName + "님과의 대화 ο(=•ω＜=)ρ⌒☆";
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
