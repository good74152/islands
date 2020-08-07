安裝方法 : git clone此repository, 接著開啟eclipse並點選左上角的File -> Import -> General -> Existing Projects into workspace -> 選取service資料夾 -> finish

使用方法(使用前記得先project->clean) : 隨便開啟一個java檔後, 點選Run -> Run As -> Run on server, 隨便選擇一個localhost(預設為J2EE Preview at localhost), 就能使用了

目前已實現功能 : 
1. Read

(1)http://localhost:8080/service/DemoService.svc/ 顯示EntitySet

(2)http://localhost:8080/service/DemoService.svc/$metadata 顯示Metadata

(3)http://localhost:8080/service/DemoService.svc/islands 顯示islands全部資料, 目前輸入1筆

(4)http://localhost:8080/service/DemoService.svc/images 顯示images全部資料, 目前輸入3筆

(5)http://localhost:8080/service/DemoService.svc/changes 顯示changes全部資料, 目前輸入3筆

(6)http://localhost:8080/service/DemoService.svc/sessions 顯示sessions全部資料, 目前輸入3筆

(7)http://localhost:8080/service/DemoService.svc/islands(id) 顯示islands指定id的資料

(8)http://localhost:8080/service/DemoService.svc/images(id) 顯示images指定id的資料

(9)http://localhost:8080/service/DemoService.svc/changes(id) 顯示changes指定id的資料

(10)http://localhost:8080/service/DemoService.svc/sessions(id) 顯示sessions指定id的資料

2. Navigation

(1)http://localhost:8080/service/DemoService.svc/islands(id)/images 顯示特定island的所有images

(2)http://localhost:8080/service/DemoService.svc/islands(id)/changes 顯示特定island的所有changes

(3)http://localhost:8080/service/DemoService.svc/islands(id)/sessions 顯示特定island的所有sessions

(4)http://localhost:8080/service/DemoService.svc/images(id)/islands 顯示特定image的所有islands

(5)http://localhost:8080/service/DemoService.svc/sessions(id)/changes 顯示特定session的所有changes

3. query option

(1) top

(2) skip

(3) count

(4) select
  
目前未實現功能 :

1. query option的orderby與filter

2. date型態資料目前以string代替

3. sessions(id)/images

4. changes(id)/images

5. image entity的code欄位目前僅能儲存一個island
