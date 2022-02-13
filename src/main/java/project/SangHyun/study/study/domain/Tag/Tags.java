package project.SangHyun.study.study.domain.Tag;

import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.DuplicateTagsException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor
public class Tags {
    @ElementCollection
    @CollectionTable(name = "study_tag", joinColumns = @JoinColumn(name = "study_id"))
    @Column(name = "tag_name")
    private List<Tag> tags;

    public Tags(List<Tag> tags) {
        validDuplicate(tags);
        this.tags = tags;
    }

    public List<String> getTagNames() {
        return tags.stream()
                .map(tag -> tag.getName())
                .collect(Collectors.toList());
    }

    private void validDuplicate(List<Tag> tags) {
        Set<Tag> nonDuplicateTags = new HashSet<>(tags);
        if (nonDuplicateTags.size() != tags.size())
            throw new DuplicateTagsException();
    }
}
