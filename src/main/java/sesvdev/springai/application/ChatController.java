package sesvdev.springai.application;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/jokes")
public class ChatController {

    private final ChatClient client;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.client = chatClientBuilder.build();
    }

    @GetMapping("/dad")
    public String dadJokes(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {
        return client.prompt(new Prompt(message)).call().chatResponse().getResult().getOutput().getContent();
    }

    @GetMapping("/chuck-norris")
    public String chuckNorrisJokes(@RequestParam(value = "message", defaultValue = "Tell me a chuck norris joke") String message) {
        return client.prompt(new Prompt(message)).call().chatResponse().getResult().getOutput().getContent();
    }

}
