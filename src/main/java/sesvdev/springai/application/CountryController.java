package sesvdev.springai.application;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final ChatClient chatClient;

    public CountryController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping
    public String getCountries(@RequestParam(value = "message", defaultValue = "all countries in europe") String message) {
        return chatClient.prompt(new Prompt(message))
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }
}
