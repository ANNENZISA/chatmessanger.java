// Main Application Class
@SpringBootApplication
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}

// WebSocket Configuration
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-chat").withSockJS();
    }
}

// Message DTO
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String recipient;

    // Getters and setters
}

// Enum for Message Types
public enum MessageType {
    CHAT,
    JOIN,
    LEAVE
}

// WebSocket Controller
@Controller
public class WebSocketController {
    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // Handle incoming messages (e.g., save to database)
        return chatMessage;
    }
}

// REST Controller for User Authentication
@RestController
@RequestMapping("/api/user")
public class UserController {
    // Implement user-related operations and authentication
}

// REST Controller for Group Chats
@RestController
@RequestMapping("/api/group")
public class GroupChatController {
    // Implement group chat-related operations
}

// REST Controller for Private Messaging
@RestController
@RequestMapping("/api/private")
public class PrivateChatController {
    // Implement private chat-related operations
}

// User Entity Class
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // Other user details and relationships

    // Getters and setters
}

// UserRepository Interface
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

// MessageRepository Interface
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    // Custom repository methods if needed
}
