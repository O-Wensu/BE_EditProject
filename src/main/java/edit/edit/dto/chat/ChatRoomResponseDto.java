package edit.edit.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import edit.edit.entity.ChatRoom;
import edit.edit.entity.JoinChatRoom;


@Getter
@AllArgsConstructor
public class ChatRoomResponseDto {
    String roomId;
    String roomName;

    public ChatRoomResponseDto(JoinChatRoom joinChatRoom) {
        this.roomId = joinChatRoom.getChatRoom().getRoomId();
        this.roomName = joinChatRoom.getRoomName();
    }
}
