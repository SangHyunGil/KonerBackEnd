package project.SangHyun.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.advice.exception.HierarchyStructureException;

import java.util.ArrayList;
import java.util.List;

class HierarchyHelperTest {
    private static class TestEntity {
        private Long id;
        private String name;
        private TestEntity parent;

        public TestEntity(Long id, String name, TestEntity parent) {
            this.id = id;
            this.name = name;
            this.parent = parent;
        }

        public TestEntity getParent() {
            return parent;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    private static class TestEntityDto {
        private Long id;
        private String name;
        private List<TestEntityDto> children;

        public TestEntityDto(Long id, String name, List<TestEntityDto> children) {
            this.id = id;
            this.name = name;
            this.children = children;
        }

        public static TestEntityDto create(TestEntity testEntity) {
            return new TestEntityDto(testEntity.getId(), testEntity.getName(), new ArrayList<>());
        }

        public List<TestEntityDto> getChildren() {
            return children;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    @DisplayName("계층 구조를 만든다.")
    void convertTest() {
        // given
        TestEntity m1 = new TestEntity(1L,"myEntity1", null);
        TestEntity m8 = new TestEntity(8L,"myEntity8", null);
        TestEntity m2 = new TestEntity(2L,"myEntity2", m1);
        TestEntity m3 = new TestEntity(3L,"myEntity3", m1);
        TestEntity m4 = new TestEntity(4L,"myEntity4", m2);
        TestEntity m5 = new TestEntity(5L,"myEntity5", m2);
        TestEntity m7 = new TestEntity(7L,"myEntity7", m3);
        TestEntity m6 = new TestEntity(6L,"myEntity6", m4);
        List<TestEntity> testEntities = List.of(m1, m8, m2, m3, m4, m5, m7, m6);

        HierarchyHelper hierarchyHelper = HierarchyHelper.of(testEntities, TestEntityDto::create, TestEntity::getParent, TestEntity::getId, TestEntityDto::getChildren);

        // when
        List<TestEntityDto> result = hierarchyHelper.convertToHierarchyStructure();

        // then
        Assertions.assertEquals(2, result.size()); // root
        Assertions.assertEquals(2, result.get(0).getChildren().size()); // m1's child
        Assertions.assertEquals(2, result.get(0).getChildren().get(0).getChildren().size()); // m2's child
        Assertions.assertEquals(1, result.get(0).getChildren().get(0).getChildren().get(0).getChildren().size()); // m4's child
        Assertions.assertEquals(1, result.get(0).getChildren().get(1).getChildren().size()); // m3's child
        Assertions.assertEquals(0, result.get(1).getChildren().size()); // m8's child
    }

    @Test
    @DisplayName("어떤 자식의 부모는, 반드시 자식보다 앞서야 한다.")
    void convertExceptionByNotOrderedValueTest() {
        // given
        TestEntity m1 = new TestEntity(1L,"myEntity1", null);
        TestEntity m8 = new TestEntity(8L,"myEntity8", null);
        TestEntity m2 = new TestEntity(2L,"myEntity2", m1);
        TestEntity m3 = new TestEntity(3L,"myEntity3", m1);
        TestEntity m4 = new TestEntity(4L,"myEntity4", m2);
        TestEntity m5 = new TestEntity(5L,"myEntity5", m2);
        TestEntity m7 = new TestEntity(7L,"myEntity7", m3);
        TestEntity m6 = new TestEntity(6L,"myEntity6", m4);

        List<TestEntity> testEntities = List.of(m1, m8, m3, m4, m2, m5, m7, m6);

        HierarchyHelper hierarchyHelper = HierarchyHelper.of(testEntities, TestEntityDto::create, TestEntity::getParent, TestEntity::getId, TestEntityDto::getChildren);

        // when, then
        Assertions.assertThrows(HierarchyStructureException.class, () -> hierarchyHelper.convertToHierarchyStructure());
    }

    @Test
    @DisplayName("부모가 없는 루트는, 항상 제일 앞에 있어야 한다.")
    void convertExceptionByNotOrderedValueNullsLastTest() {
        // given
        TestEntity m1 = new TestEntity(1L,"myEntity1", null);
        TestEntity m8 = new TestEntity(8L,"myEntity8", null);
        TestEntity m2 = new TestEntity(2L,"myEntity2", m1);
        TestEntity m3 = new TestEntity(3L,"myEntity3", m1);
        TestEntity m4 = new TestEntity(4L,"myEntity4", m2);
        TestEntity m5 = new TestEntity(5L,"myEntity5", m2);
        TestEntity m7 = new TestEntity(7L,"myEntity7", m3);
        TestEntity m6 = new TestEntity(6L,"myEntity6", m4);

        List<TestEntity> testEntities = List.of(m2, m3, m4, m5, m7, m6, m1, m8);

        HierarchyHelper hierarchyHelper = HierarchyHelper.of(testEntities, TestEntityDto::create, TestEntity::getParent, TestEntity::getId, TestEntityDto::getChildren);

        // when, then
        Assertions.assertThrows(HierarchyStructureException.class, () -> hierarchyHelper.convertToHierarchyStructure());
    }
}