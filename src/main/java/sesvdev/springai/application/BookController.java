package sesvdev.springai.application;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.*;
import sesvdev.springai.domain.model.Author;

import java.util.Map;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final ChatModel chatModel;

    private final ChatClient chatClient;

    public BookController(ChatModel chatModel, ChatClient.Builder chatClient) {
        this.chatModel = chatModel;
        this.chatClient = chatClient.build();
    }



    @GetMapping("/by-author")
    public Author booksByAuthor (@RequestParam(value = "author", defaultValue = "Martin Fowler") String author) {
        BeanOutputConverter<Author> beanOutputConverter =
                new BeanOutputConverter<>(Author.class);

        String format = beanOutputConverter.getFormat();

        String template = """
        Generate 10 books for {author}.
        {format}
        """;

        Generation generation = chatModel.call(
                new Prompt(new PromptTemplate(template, Map.of("author", author, "format", format)).createMessage())).getResult();

       return beanOutputConverter.convert(generation.getOutput().getContent());
    }
}
