# FileConcatenation 
Корневой считается папка **.\src\main\resources**, т.к. в задании не указано, как она задается.  
Поиск директивы require выполняется на отдельной строке:  
**require ‘<путь к другому файлу от корневого каталога>’**  
Кавычки должны быть именно такими, символ разделителя '\\' или '/'. Если такого файла нет, то ничего не происходит.  
Если обнаружен цикл, то выводится сообщение о том, что в конкретном файле найден цикл, и программа завершается.  
Если отсортировать удалось, то происходит конкатенация в файл **.\src\main\resources\result.txt** (в задании не указано, в какой файл нужно записывать результат).  
