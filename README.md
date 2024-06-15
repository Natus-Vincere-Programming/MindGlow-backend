# Інструкція (локальне встановлення бекенду для тестування - Windows)
## Підготовка до запуску
1. Спочатку необхідно завантажити Java 21 JDK за цим [посиланням](https://www.oracle.com/cis/java/technologies/downloads/#jdk21-windows)
   - Вибрати x64 MSI Installer ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/95fbe08c-8a6e-45c1-8ec7-1a79f8ee6dd0)
   - Встановити Java за допомогою завантаженого інсталера. Запам'ятати шлях, де буде встановлена Java ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/efc21c20-a96f-40cb-948a-1d324ff92b40) \
2. Встановити MySQL за цим [посилання](https://dev.mysql.com/downloads/installer/)
   - Вибрати Windows (x86, 32-bit), MSI Installer, той що має більший розмір Windows ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/b16ff2e2-30df-4810-ba8a-93d4da607af4)
   - Запустити завантажений файл
   - Вибрати Server only ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/684b1b4e-4654-47ab-ae65-33b8e13fbf05)
   - Вибрати Execute ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/4d6737dc-6bc9-431c-bff3-64aeb2c49280)
   - Залишити налаштування такі як тут ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/ac7bf0a9-7fa5-4d60-8490-cc6c6ee78b21)
   - Так як тут ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/c774d367-cea0-453f-9b87-4d38588d3686)
   - Встановити пароль `12345678` для root користувача (**встановити пароль обов'язково такий самий**) ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/55c75c34-7ca9-4856-b60f-314454caedba)
   - Залишити налаштування як тут ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/00c9c394-1e97-40f4-8701-0db4875e0f78) \ ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/60aa0ff4-82b5-4dfe-a843-7248bb18ff6e) \ Execute ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/41e31501-bf8b-4289-a54d-3ac28df5ba3e)
   - Закінчити налаштування
3. Налаштування бази даних
   - Потрібно за [посиланням](https://www.heidisql.com/download.php) встановити HeidiSQL.
   - Зробити дії за фото ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/b358feb2-d253-43ac-bda7-171a5982a511) Ввести пароль який вводили при встановленні MySQL. Нажати кнопку відкрити ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/cb034492-a14f-4f14-96d1-e09b570e6f1c) Нажати лівою кнопкою мишки на Unnamed і створити нову базу даних ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/0839c45a-c106-4ade-a4e4-eede907738a7)
   - **Обов'язково назвати базу даних `test`** і нажати кнопку `Гаразд` ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/e441ba3f-ca83-413d-b713-09cd427a26cd)
   - Якщо з'явилася база даних `test` значить все ок! ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/ce4e98b1-000d-4d77-a22c-492b7c743fe5)
## Запуск програми \
Для запуску програми потрібно завантажити `backend-v.0.0.2.zip` за [посиланням](https://github.com/Natus-Vincere-Programming/MindGlow-backend/releases) і розпакувати \
- Розпакувати, відкрити через текстовий редактор, наприклад Notepad++, файл `start.bat`. Встановити шлях до Java (має бути шлях до файлу java.exe) який ми запам'ятали раніше. ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/682ea86b-73d8-44eb-830a-e2f60147ef6a) ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/1197e131-3a14-4c53-b00e-663319fc8e1c)
- Щоб легко отримати шлях можна вибрати файл `java.exe` і натиснути `Ctrl + Shift + C`. Шлях скопіюється. ![image](https://github.com/Natus-Vincere-Programming/MindGlow-backend/assets/113195769/7d24d3db-9f96-4df3-90d4-5a6f3a7612ad)

- Зберегти відредагований файл з новим шляхом до java.exe
- Запустити його \
  І бекенд має працювати :D
















 



