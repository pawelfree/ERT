package pl.pd.emir.entity.utils;

import pl.pd.emir.entity.utils.ReflectionValidationUtils;
import javax.persistence.Embedded;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.pd.emir.entity.annotations.ValidateCompleteness;
import pl.pd.emir.entity.annotations.Validators;

public class ValidationUtilsTest {

    public ValidationUtilsTest() {
    }

    @Test
    public void testIsComplete1() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object());
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, new Object(), new Object());

        assertTrue(ReflectionValidationUtils.isComplete(entity, true)); //Encja kompletna
    }

    @Test
    public void testIsComplete2() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());

        assertTrue(ReflectionValidationUtils.isComplete(entity, true)); //Encja kompletna mimo pustych ignorowanych pól
    }

    @Test
    public void testIsComplete3() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, null, new Object(), new Object(), new Object(), embeddable, null, new Object());

        assertTrue(ReflectionValidationUtils.isComplete(entity, false)); //Encja kompletna mimo pustego obiektu wyceny (valuationReporting = false)
    }

    @Test
    public void testIsComplete4() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, null, new Object(), new Object(), new Object(), embeddable, null, new Object());

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na pusty obiekt wyceny (valuationReporting = true)
    }

    @Test
    public void testIsComplete5() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(null, "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na puste pole dowolnego typu
    }

    @Test
    public void testIsComplete6() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), null, TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na puste pole typu string
    }

    @Test
    public void testIsComplete7() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), "", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na pusty ciąg znaków
    }

    @Test
    public void testIsComplete8() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), "string", null, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na puste pole typu wyliczeniowego
    }

    @Test
    public void testIsComplete9() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.ERR, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na wartość ERR w polu typu wyliczeniowego
    }

//    @Test
//    public void testIsComplete10() {
//        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(null, "string", TestEnum.TEST_VALUE, new Object(), null);
//        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());
//
//        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na puste pole dowolnego typu w polu osadzonym
//    }
//
//    @Test
//    public void testIsComplete11() {
//        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), null, TestEnum.TEST_VALUE, new Object(), null);
//        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());
//
//        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na puste pole typu string w polu osadzonym
//    }
//
//    @Test
//    public void testIsComplete12() {
//        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "", TestEnum.TEST_VALUE, new Object(), null);
//        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());
//
//        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na pusty ciąg znaków w polu osadzonym
//    }
//
//    @Test
//    public void testIsComplete13() {
//        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", null, new Object(), null);
//        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, new Object());
//
//        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na puste pole typu wyliczeniowego w polu osadzonym
//    }
    @Test
    public void testIsComplete14() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, null, null);
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), null, new Object(), embeddable, null, new Object());

        assertTrue(ReflectionValidationUtils.isComplete(entity, true)); //Encja kompletna mimo obecności pustych pól w grupie (nie wszystkich)
    }

//    @Test
//    public void testIsComplete15() {
//        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
//        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null, null, new Object(), embeddable, null, new Object());
//
//        assertTrue(ReflectionValidationUtils.isComplete(entity, true)); //Encja kompletna mimo obecności pustych pól w grupie (pole zainicjalizowane tylko w polu osadzonym)
//    }
    @Test
    public void testIsComplete16() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, null, null);
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null, null, new Object(), embeddable, null, new Object());

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na pustą grupę (wszystkie pola w grupie sa równe null)
    }

    @Test
    public void testIsComplete17() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), null, embeddable, null, new Object());

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na puste pole z wieloma walidatorami
    }

    @Test
    public void testIsComplete18() {
        TestEmbeddableEntity embeddable = new TestEmbeddableEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), null);
        TestEntity entity = new TestEntity(new Object(), "string", TestEnum.TEST_VALUE, new Object(), new Object(), new Object(), new Object(), embeddable, null, null);

        assertFalse(ReflectionValidationUtils.isComplete(entity, true)); //Encja niekompletna ze względu na puste pole w klasie bazowej
    }

    private class TestEntity extends TestSuperEntity {

        @ValidateCompleteness(subjectClass = TestEntity.class)
        private final Object objectFiled; //dowolny typ

        @ValidateCompleteness(subjectClass = TestEntity.class)
        private final String stringFiled; //ciąg znaków

        @ValidateCompleteness(subjectClass = TestEntity.class)
        private final Enum enumFiled; //typ wyliczeniowy

        @ValidateCompleteness(subjectClass = TestEntity.class, checkValuationReporting = true)
        private final Object valuationCheckFiled; //pole wymagające sprawdzenia parametru ValuationReporting

        @ValidateCompleteness(subjectClass = TestEntity.class, orGroup = "group1")
        private final Object groupFiled1; //pole wymagające walidacji w grupie

        @ValidateCompleteness(subjectClass = TestEntity.class, orGroup = "group1")
        private final Object groupFiled2; //pole wymagające walidacji w grupie

        @Validators({
            @ValidateCompleteness(subjectClass = Object.class),
            @ValidateCompleteness(subjectClass = TestEntity.class)
        })
        private final Object multipleValidatorFiled; //pole wymagające walidacji w grupie

        @Embedded
        @ValidateCompleteness(subjectClass = TestEntity.class)
        private final TestEmbeddableEntity embeddedFiled; //pole osadzone

        private final Object ignoredFiled; //pole ignorowane ze względu na brak adnotacji @ValidateCompleteness

        public TestEntity(Object objectFiled, String stringFiled, Enum enumFiled, Object valuationCheckFiled,
                Object groupFiled1, Object groupFiled2, Object multipleValidatorFiled,
                TestEmbeddableEntity embeddedFiled, Object ignoredFiled, Object sueperFiled) {
            super(sueperFiled);
            this.objectFiled = objectFiled;
            this.stringFiled = stringFiled;
            this.enumFiled = enumFiled;
            this.valuationCheckFiled = valuationCheckFiled;
            this.groupFiled1 = groupFiled1;
            this.groupFiled2 = groupFiled2;
            this.multipleValidatorFiled = multipleValidatorFiled;
            this.embeddedFiled = embeddedFiled;
            this.ignoredFiled = ignoredFiled;
        }
    }

    private class TestSuperEntity {

        @ValidateCompleteness(subjectClass = TestEntity.class)
        private final Object sueperFiled;

        public TestSuperEntity(Object sueperFiled) {
            this.sueperFiled = sueperFiled;
        }

    }

    private class TestEmbeddableEntity {

        @ValidateCompleteness(subjectClass = TestEntity.class)
        private final Object objectFiled; //dowolny typ

        @ValidateCompleteness(subjectClass = TestEntity.class)
        private final String stringFiled; //ciąg znaków

        @ValidateCompleteness(subjectClass = TestEntity.class)
        private final Enum enumFiled; //typ wyliczeniowy

        @ValidateCompleteness(subjectClass = TestEntity.class, orGroup = "group1")
        private final Object groupFiled3; //pole wymagające walidacji w grupie

        @ValidateCompleteness(subjectClass = Object.class)
        private final Object ignoredFiled; //pole ignorowane ze względu na zarejestrowanie walidatora na inny typ

        public TestEmbeddableEntity(Object objectFiled, String stringFiled, Enum enumFiled, Object groupFiled3,
                Object ignoredFiled) {
            this.objectFiled = objectFiled;
            this.stringFiled = stringFiled;
            this.enumFiled = enumFiled;
            this.groupFiled3 = groupFiled3;
            this.ignoredFiled = ignoredFiled;
        }

    }

    private enum TestEnum {

        TEST_VALUE, ERR;
    }

}
