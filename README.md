# PrintManager2023
Программа эмулирующая работу диспетчера печати документов.
Интерфейс реализован с использванием технологии Java FX.

Диспетчер печати может работать с несколькими типами документов.
Каждый тип документа должен иметь уникальные реквизиты: продолжительность печати, наименование типа документа, размер бумаги - данные параметры задаются в главном окне с использованием 
поля для ввода времени продолжительности печати и комбо-боксов для установки размера и типа документа.

Диспетчер помещает в очередь печати неограниченное количество документов.
При этом каждый документ может быть обработан, только если в это же время не обрабатывается другой документ, время обработки каждого документа равно продолжительности печати данного документа.
В диспетчерк реализованы следующие методы:
* Остановка диспетчера. Печать документов в очереди отменяется. На выходе должен быть список ненапечатанных документов
(реализовано в момент закрытия окна, при нажатии на эконку "крестик", после этого возникает новое окно со списком всех напечатанных документов).
* Принять документ на печать. Метод не должен блокировать выполнение программы(Заполнить поля, комбо боксы и нажать на кнопку "Отправить на печать").
* Отменить печать принятого документа, если он еще не был напечатан (Нажать на кнопку "Отмена печати").
* Получить отсортированный список напечатанных документов. Список может быть отсортирован на выбор: по порядку печати, по типу документов,
по продолжительности печати, по размеру бумаги (Нажать на одну из кнопок в правой части окна).
* Рассчитать среднюю продолжительность печати напечатанных документов (Нажать на кнопку "Расчитать среднее время печати")