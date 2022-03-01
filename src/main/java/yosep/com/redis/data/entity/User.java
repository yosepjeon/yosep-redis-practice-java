package yosep.com.redis.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yosep.com.redis.data.code.UserRole;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true, length = 50)
    private String userId;
    @Column(nullable = false, length = 50)
    private String password;
    @Column(nullable = false, length = 50)
    private String name;
    @Enumerated(EnumType.STRING)
    UserRole role;
}
