package com.natusvincere.mindglow;

import com.natusvincere.mindglow.auth.AuthenticationService;
import com.natusvincere.mindglow.auth.RegisterRequest;
import com.natusvincere.mindglow.subject.Subject;
import com.natusvincere.mindglow.subject.SubjectRepository;
import com.natusvincere.mindglow.subject.SubjectService;
import com.natusvincere.mindglow.user.UserRepository;
import com.natusvincere.mindglow.user.UserService;
import com.natusvincere.mindglow.user.request.EnableUserRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.natusvincere.mindglow.user.Role.*;

@SpringBootApplication
public class MindBlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindBlowApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service,
            UserService userService,
            SubjectRepository subjectRepository,
            UserRepository repository,
            SubjectService subjectService,
            UserRepository userRepository) {
        return args -> {
            String[] surnames = {
                    "Петренко", "Іваненко", "Коваленко", "Бойко", "Ковальчук", "Мельник", "Поліщук", "Кравчук", "Олійник", "Шевченко",
                    "Ткаченко", "Кузьменко", "Гончаренко", "Мазур", "Лисенко", "Костенко", "Бондаренко", "Тарасенко", "Марченко", "Юрченко",
                    "Савченко", "Василенко", "Матвієнко", "Кравець", "Орел", "Шаповал", "Білоус", "Кушнір", "Шевчук", "Поліщук",
                    "Олійник", "Сорока", "Ткач", "Кириченко", "Васильченко", "Галушко", "Бабенко", "Литвин", "Горбатюк", "Костюк",
                    "Терещенко", "Грищенко", "Лук'яненко", "Білоконь", "Діденко", "Панченко", "Герасименко", "Боднар", "Волошин", "Кравченко",
                    "Тимошенко", "Орлов", "Пономаренко", "Литвиненко", "Клименко", "Черненко", "Білоус", "Степаненко", "Підгорний", "Макаренко",
                    "Сергієнко", "Карпенко", "Волошин", "Блохін", "Устинов", "Богданенко", "Ковтун", "Терещук", "Міщенко", "Павленко",
                    "Руденко", "Кузьмін", "Собко", "Романенко", "Цибулько", "Ярошенко", "Гнатюк", "Гриценко", "Кузьмін", "Собко",
                    "Романенко", "Цибулько", "Ярошенко", "Гнатюк", "Гриценко", "Кузьмін", "Собко", "Романенко", "Цибулько", "Ярошенко",
                    "Гнатюк", "Гриценко", "Кузьмін", "Собко", "Романенко", "Цибулько", "Ярошенко", "Гнатюк", "Гриценко", "Кузьмін"
            };
            String[] names = {
                    "Іван", "Петро", "Олександр", "Михайло", "Василь", "Дмитро", "Сергій", "Андрій", "Олег", "Максим",
                    "Юрій", "Анатолій", "Олексій", "Роман", "Володимир", "Богдан", "Тарас", "Степан", "Ігор", "Ярослав",
                    "Віктор", "Микола", "Левко", "Артем", "Вадим", "Денис", "Захар", "Кирило", "Марк", "Остап",
                    "Павло", "Руслан", "Тимофій", "Євген", "Ян", "Адам", "Борис", "Валерій", "Георгій", "Давид",
                    "Єлисей", "Зеновій", "Ілля", "Костянтин", "Леонід", "Мирослав", "Назар", "Орест", "Пилип", "Ростислав",
                    "Святослав", "Теодор", "Федір", "Христян", "Чеслав", "Шон", "Юхим", "Яків", "Арнольд", "Бенедикт",
                    "Веніамін", "Гнат", "Данило", "Єремій", "Зіновій", "Ісаак", "Клим", "Лук'ян", "Мечислав", "Нестор",
                    "Орхип", "Прохор", "Радимир", "Семен", "Тихон", "Фома", "Хома", "Цезар", "Шалва", "Юліан",
                    "Ярема", "Аркадій", "Броніслав", "Віталій", "Григорій", "Добромир", "Єзекіїль", "Зенон", "Ісидор", "Корній",
                    "Любомир", "Милан", "Нікіта", "Орел", "Порфирій", "Ратибор", "Славута", "Троян", "Федосій", "Хвалибог",
                    "Цицерон", "Шулер", "Юрій", "Ясен", "Артур", "Брячислав", "Всеволод", "Густав", "Драгомир", "Єфрем"
            };
            var admin = RegisterRequest.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            if (userRepository.findById(1).isEmpty()) {
                service.register(admin);
                userService.enableUser(new EnableUserRequest(1, admin.getFirstName(), admin.getLastName(), ADMIN));
            }
            /*for (int i = 2; i < 101; i++) {
                var teacher = RegisterRequest.builder()
                        .firstName(names[i - 2])
                        .lastName(surnames[i - 2])
                        .email(names[i - 1].toLowerCase() + surnames[i - 1].toLowerCase() + "@mail.com")
                        .password("password")
                        .role(TEACHER)
                        .build();
                System.out.println("Student token: " + service.register(teacher).getAccessToken());
                userService.enableUser(new EnableUserRequest(i, teacher.getFirstName(), teacher.getLastName(), TEACHER));
            }
            for (int i = 101; i < 201; i++) {
                var teacher = RegisterRequest.builder()
                        .firstName(names[i - 101])
                        .lastName(surnames[i - 101])
                        .email(names[i - 101].toLowerCase() + surnames[i - 101].toLowerCase() + "@mail.com")
                        .password("password")
                        .role(TEACHER)
                        .build();
                System.out.println("Student token: " + service.register(teacher).getAccessToken());
                userService.enableUser(new EnableUserRequest(i, teacher.getFirstName(), teacher.getLastName(), TEACHER));
            }
            for (int i = 201; i < 301; i++) {
                var teacher = RegisterRequest.builder()
                        .firstName(names[i - 201])
                        .lastName(surnames[i - 201])
                        .email(names[i - 201].toLowerCase() + surnames[i - 201].toLowerCase() + "@mail.com")
                        .password("password")
                        .role(TEACHER)
                        .build();
                System.out.println("Student token: " + service.register(teacher).getAccessToken());
                ;
            }

            for (int i = 301; i < 401; i++) {
                var teacher = RegisterRequest.builder()
                        .firstName(names[i - 301])
                        .lastName(surnames[i - 301])
                        .email(names[i - 301].toLowerCase() + surnames[i - 301].toLowerCase() + "@mail.com")
                        .password("password")
                        .role(STUDENT)
                        .build();
                System.out.println("Student token: " + service.register(teacher).getAccessToken());
                userService.enableUser(new EnableUserRequest(i, teacher.getFirstName(), teacher.getLastName(), STUDENT));
            }

            String[] subjects = {
                    "Математика", "Фізика", "Хімія", "Біологія", "Історія", "Географія", "Англійська мова", "Українська мова",
                    "Література", "Інформатика", "Фізична культура", "Музика", "Мистецтво", "Технології", "Геометрія",
                    "Астрономія", "Екологія", "Соціологія", "Правознавство", "Економіка"
            };
            String[] subjectDescriptions = {
                    "Наука про структури, порядок та відношення",
                    "Наука про матерію, енергію та взаємодію між ними",
                    "Наука про атоми, молекули та їх взаємодії",
                    "Наука про живі організми та їх структуру",
                    "Наука про минулі події та їх вплив",
                    "Наука про місця та властивості Землі",
                    "Вивчення англійської мови та літератури",
                    "Вивчення української мови та літератури",
                    "Вивчення літературних творів та їх аналіз",
                    "Вивчення комп'ютерних систем та програмування",
                    "Вивчення фізичних вправ та здорового способу життя",
                    "Вивчення музичних теорій та практики",
                    "Вивчення різних форм мистецтва та їх створення",
                    "Вивчення технологічних процесів та їх застосування",
                    "Вивчення форм, розмірів та властивостей простору",
                    "Вивчення всесвіту за межами Землі",
                    "Вивчення взаємодії між організмами та їхнім середовищем",
                    "Вивчення соціального поведінки та суспільства",
                    "Вивчення системи правил, які регулюють суспільство",
                    "Вивчення виробництва, розподілу та споживання товарів та послуг"
            };
            for (int i = 0; i < 20; i++) {
                subjectRepository.save(Subject.builder()
                        .name(subjects[i])
                        .description(subjectDescriptions[i])
                        .teacher(repository.findById(i + 2).orElseThrow())
                        .build());
                subjectService.generateCode(i + 1);
            }*/
        };
    }

}
