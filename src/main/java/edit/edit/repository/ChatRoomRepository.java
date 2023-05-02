package edit.edit.repository;

import edit.edit.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(String roomId);

//    Optional<ChatRoom> findByJoinChatRooms

    void deleteByRoomId(String roomId);

//    List<ChatRoom> findAllByHostIdOrGuestIdOrderByModifiedAtDesc(Long hostId, Long guestId);
}
