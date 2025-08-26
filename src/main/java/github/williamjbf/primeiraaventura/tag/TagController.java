package github.williamjbf.primeiraaventura.tag;

import github.williamjbf.primeiraaventura.tag.model.Tag;
import github.williamjbf.primeiraaventura.tag.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(){
        List<Tag> allTags = tagService.getAllTags();

        return ResponseEntity.ok(allTags);
    }

}
