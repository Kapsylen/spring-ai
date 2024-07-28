package sesvdev.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient client;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.client = chatClientBuilder.build();
    }

    @GetMapping("/dad-jokes")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {
        return client.prompt(new Prompt(message)).call().chatResponse().getResult().getOutput().getContent();
    }

}
