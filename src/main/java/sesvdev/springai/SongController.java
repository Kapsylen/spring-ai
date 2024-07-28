package sesvdev.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.parser.ListOutputParser;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SongController {

    private final ChatClient chatClient;

    public SongController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

/*    @GetMapping("/songs")
    public String getSongsByArtist(@RequestParam(value = "artist", defaultValue = "Johnny Cash") String artist) {
        var message = """
                Please give me the top 10 songs for then {artist}. If you do not know the answer, just say "I do not know".
                """;
        var promptTemplate = new PromptTemplate(message, Map.of("artist", artist));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }*/

    @GetMapping("/songs")
    public List<String> getSongsByArtist(@RequestParam(value = "artist", defaultValue = "Johnny Cash") String artist) {
        var message = """
                Please give me the top 10 songs for then {artist}. If you do not know the answer, just say "I do not know".
                """;

        var outputConverter = new ListOutputConverter(new DefaultConversionService());

        var promptTemplate = new PromptTemplate(message, Map.of("artist", artist, "format", outputConverter.getFormat()));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return outputConverter.convert(response.getResult().getOutput().getContent());
    }
}
