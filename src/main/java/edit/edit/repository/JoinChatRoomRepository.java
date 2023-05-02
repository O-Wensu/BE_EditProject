package edit.edit.repository;

import edit.edit.entity.JoinChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JoinChatRoomRepository extends JpaRepository<JoinChatRoom, Long> {
    Optional<List<JoinChatRoom>> findAllByMemberId(Long member_id);
    JoinChatRoom findByChatRoomId(Long chatRoom_id);
}
