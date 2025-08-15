package github.williamjbf.primeiraaventura.tag.service;

import github.williamjbf.primeiraaventura.tag.model.Tag;
import github.williamjbf.primeiraaventura.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagsByIds(List<Long> ids) {
        return tagRepository.findByIdIn(ids);
    }

    public Tag createTag(String nome) {
        if (tagRepository.existsByNomeIgnoreCase(nome)) {
            throw new IllegalArgumentException("Tag já existe: " + nome);
        }
        return tagRepository.save(new Tag(nome));
    }
}
