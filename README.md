ЗАЧЕЧАНИЯ:
• Не нужен отдельный класс для имплементации UserDetailsService. Правильно будет, если UserService будет имплементить сразу два интерфейса
+Исправлено

• UserDetails тоже не нужен отделный (см. 3 пункт)
+Исправлено

• Не используй EAGER
+Исправлено (добавлен @Fetch(FetchMode.JOIN))
• Перед отправкой кода проходись по всем классам базовым код-стайлом (Ctrl+Alt+L) и форматированием импортов (Ctrl+Alt+O)
+Сделано
• Вместо Collection<Role> лучше использовать Set<Role> 
+Исправлено
• Добавь класс, который будет заполнять таблицу пользователями при запуске приложения (Логин: admin - пароль: admin, логин: user - пароль: user). Установи параметр spring.jpa.hibernate.ddl-auto=create-drop
+Добавлен класс DataInitializer в config
• Используй или DAO или JpaRepository (что-то одно)
+Оставила только JpaRepository
• Добавь сервис для ролей, в контроллере работай именно с ним
+Добавлен
• В контроллере работай с интерфейсами, а не с имплементациями
+Сделано
