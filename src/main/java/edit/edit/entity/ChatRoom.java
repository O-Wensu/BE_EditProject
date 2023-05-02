package edit.edit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString(exclude = "joinChatRooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<JoinChatRoom> joinChatRooms = new ArrayList<>();

    public ChatRoom() {
        this.roomId = UUID.randomUUID().toString();
    }

    public void addJoinChatRoom(JoinChatRoom joinChatRoom) {
        joinChatRooms.add(joinChatRoom);
    }
}
