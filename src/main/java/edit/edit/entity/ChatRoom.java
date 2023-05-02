package edit.edit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "joinChatRooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String roomName;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<JoinChatRoom> joinChatRooms;

    public static ChatRoom of(Member receiver) {
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(receiver.getNickname() + "님과의 대화 ο(=•ω＜=)ρ⌒☆")
                .joinChatRooms(new ArrayList<>())
                .build();
    }

    public void addJoinChatRoom(JoinChatRoom joinChatRoom) {
        joinChatRooms.add(joinChatRoom);
    }
}
