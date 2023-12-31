# SYNT-BIO-test-task
Тестовое задание для проекта SYNT-BIO

В ходе выполнения данного задания были реализованы тестовые сценарии:

API:
* JobSchedulerTests.testValidEntity - проверка позитивного сценария
* JobSchedulerTests.testInvalidEntity - проверка сценария для невалидной сущности
* JobSchedulerTests.testTimeOutEntity - проверка сценария для сущности с тайм-аутом
* JobSchedulerTests.testErrorEntity - проверка сценария для сущности, выдающей ошибку

UI:
* GitHubPageTests.testLoginButtonRedirectsToLoginPage - проверка работы кнопки "LogIn"
* GitHubPageTests.testSignupButtonRedirectsToSignupPage - проверка работы кнопки "SignUp"
* GitHubPageTests.testSearchRepository - проверка работы поиска и валидности реультата
* GitHubPageTests.testNavigationDropdownsAppears - проверка появления выпадающих списков в раделе навигации в хедере

 
### Текст задания:

API часть:

Есть эндпоинт, который создает задачу по обработке некоторой сущности, которая уже есть в системе

POST https://test.server.org/scheduleJob (URL ненастоящий!)

В боди отправляем айдишник сущности в виде { “id” : <string> }

В ответ приходит 200, означающая, что задача поставлена в очередь на выполнение. В теле ответа приходит айдишник созданной задачи в виде { “jobId”: <string> }

Выполнение задачи может занять от 10 секунд до двух минут в зависимости от загруженности системы. Если время превысило 2 минуты, это считается ошибкой.

Есть эндопоинт, позволяющий проверить статус запущенной задачи GET https://test.server.org/job/{jobId}, где jobId – айдишник задачи

В ответ возвращает 200 с объектом вида { “jobStatus”: <status> } , где статус может быть один из: IN_PROGRESS (взята в работу), SUCCESS (завершена успешно), ERROR (ошибка. Например, при создании джобы передали айдишник сущности, которой нет в системе)

Также эндпоинт может вернуть 404, если передать айдишник несуществующей задачи.

Задача: покрыть эти эндпоинты тестами. Для написания тестов использовать Java, Maven, TestNG, Rest Assured

Результат: 1+ файлов с необходимыми классами. Прислать архивом

Что имеется (при отсутствии времени можно не реализовывать, хотя реализация будет огромным плюсом):

Все необходимые DTO для работы с эндпоинтами: ScheduleJobRequest, ScheduleJobResponse, GetJobStatusResponse. Enum со статусами: JobStatus

Также есть вспомогательный метод, позволяющий ждать наступление какого-то события:

Utils.waitFor

static void waitFor(long timeoutSec, Callable<Boolean> action, long pollInterval, String message)

где

timeoutSec – время ожидания в секундах

action – функция, которая проверяет наступление события. Возвращает true, если событие наступило, иначе - false

pollInterval – промежуток времени между выполнениями action

message – сообщение об ошибке в случае, когда мы не дождались результата

UI часть: Задание – протестировать стартовую страницу https://github.com. Требуется составить чеклист с проверками (не полноценные кейсы) и часть из них (3-5, больше не надо) покрыть UI автотестами. Использовать Java, Maven, TestNG, JDI light

Результат: В том же проекте, что и API тесты, создать файл с чеклистом (формат произвольный, положить в srt/test/resources) и 1+ класс с автотестами

