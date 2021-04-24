package test.com.wuxp.security.example.mapstruct;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * https://blog.csdn.net/zhige_me/article/details/80699784
 */
public class PersonConverterTest {


    @Test
    public void test() {
        Person person = new Person(1L, "zhige", "zhige.me@gmail.com", new Date(), new User(1));
        PersonDTO personDTO = PersonConverter.INSTANCE.domain2dto(person);
        Assert.assertNotNull(personDTO);
        Assert.assertEquals(personDTO.getId(), person.getId());
        Assert.assertEquals(personDTO.getName(), person.getName());
        Assert.assertEquals(personDTO.getBirth(), person.getBirthday());
        String format = DateFormatUtils.format(personDTO.getBirth(), "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(personDTO.getBirthDateFormat(), format);
        Assert.assertEquals(personDTO.getBirthExpressionFormat(), format);

        List<Person> people = new ArrayList<>();
        people.add(person);
        List<PersonDTO> personDTOs = PersonConverter.INSTANCE.domain2dto(people);
        Assert.assertNotNull(personDTOs);
    }

}
