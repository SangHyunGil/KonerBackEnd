package project.SangHyun.helper;

import project.SangHyun.advice.exception.HierarchyStructureException;
import project.SangHyun.study.studycomment.dto.response.StudyCommentFindResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class HierarchyHelper<K, E, D> {
    private final List<D> roots;
    private final Map<K, D> map;
    private final List<E> entities;
    private final Function<E, D> toDto;
    private final Function<E, E> getParent;
    private final Function<E, K> getKey;
    private final Function<D, List<D>> getChildren;


    public static <E, D, K> HierarchyHelper of(List<E> entities, Function<E, D> toDto, Function<E, E> getParent, Function<E, K> getKey, Function<D, List<D>> getChildren) {
        return new HierarchyHelper(new ArrayList(), new HashMap(), entities, toDto, getParent, getKey, getChildren);
    }

    private HierarchyHelper(List<D> roots, Map<K, D> map, List<E> entities, Function<E, D> toDto, Function<E, E> getParent, Function<E, K> getKey, Function<D, List<D>> getChildren) {
        this.roots = roots;
        this.map = map;
        this.entities = entities;
        this.toDto = toDto;
        this.getParent = getParent;
        this.getKey = getKey;
        this.getChildren = getChildren;
    }

    public List<D> convertToHierarchyStructure() {
        try {
            return makeHierarchyStructure();
        } catch (NullPointerException e) {
            throw new HierarchyStructureException();
        }
    }

    private List<D> makeHierarchyStructure() {
        for (E entity : entities) {
            D dto = applyToDtoFunction(entity);
            map.put(applyGetKeyFunction(entity), dto);
            if (hasParent(entity)) {
                D parentDto = map.get(applyGetKeyFunction(applyGetParentFunction(entity)));
                applyGetChildrenFunction(parentDto).add(dto);
            } else {
                roots.add(dto);
            }
        }
        return roots;
    }

    private D applyToDtoFunction(E entity) {
        return toDto.apply(entity);
    }

    private K applyGetKeyFunction(E entity) {
        return getKey.apply(entity);
    }

    private boolean hasParent(E entity) {
        return applyGetParentFunction(entity) != null;
    }

    private E applyGetParentFunction(E entity) {
        return getParent.apply(entity);
    }


    private List<D> applyGetChildrenFunction(D parentDto) {
        return getChildren.apply(parentDto);
    }
}
