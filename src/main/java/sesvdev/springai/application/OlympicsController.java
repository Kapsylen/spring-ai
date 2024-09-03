package sesvdev.springai.application;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sesvdev.springai.domain.model.Author;
import sesvdev.springai.domain.model.Country;

import java.util.List;
import java.util.Map;
import java.util.Stack;

@RestController
@RequestMapping("api/sport")
public class OlympicsController {

    private final ChatClient chatClient;
    private final ChatModel chatModel;

    public OlympicsController(ChatClient.Builder chatClientBuilder, ChatModel chatModel) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
    }

    @GetMapping("/types-in-olympics")
    public String getOlympics(@RequestParam(value = "message", defaultValue = "What sports are included in 2024 Olympics?") String message) {

        var promptTemplate = new PromptTemplate(message, Map.of("message", message));
        var prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }

    @GetMapping("/2024-olympic-medals-by-country")
    public Country getMedalsByCountry(@RequestParam(value = "country", defaultValue = "USA") String country) {

        BeanOutputConverter<Country> beanOutputConverter =
                new BeanOutputConverter<>(Country.class);

        String format = beanOutputConverter.getFormat();

        String template = """
        Generate 2024 all olympic medals by {country}.
        {format}
        """;

        Generation generation = chatModel.call(
                new Prompt(new PromptTemplate(template, Map.of("country", country, "format", format)).createMessage())).getResult();

        return beanOutputConverter.convert(generation.getOutput().getContent());

    }
}
