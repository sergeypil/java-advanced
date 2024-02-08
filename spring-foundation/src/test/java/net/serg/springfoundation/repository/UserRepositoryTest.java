package net.serg.springfoundation.repository;

import net.serg.springfoundation.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testSave() {
        // given
        User user = new User();
        user.setName("John");

        // when
        var actual = repository.save(user);

        // then
        assertThat(user).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }
}