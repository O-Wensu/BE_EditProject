package edit.edit.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatDto {

    //메시지 타입
    public enum MessageType {
        ENTER,
        TALK,
        LEAVE;
    }

    private MessageType type;
    private String sender;
    private String roomId;
    private String roomName;
    private String message;

    @Override
    public String toString() {
        return "ChatDto{" +
                "type=" + type +
                ", sender='" + sender + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

