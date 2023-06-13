# 서울시 공공와이파이 찾기 프로젝트
 현재 위치에서 가까운 20개의 서울시 와이파이 정보를 찾는 JSP프로젝트입니다.
 
<hr>

## 시작하기 전에 무엇이 필요한가요?
- 서울시 열린데이터 광장에서, 서울시 공공와이파이 조회 API KEY를 발급받습니다.
- 프로젝트에서, resources/application.properties에 "api.key"에 발급한 API KEY를 입력합니다.
- 프로젝트 루트 폴더에 publicWifi.db파일이 존재합니다. 해당 파일의 절대 경로를 "db.source"에 입력합니다.
- Tomcat 8.5버전이 필요합니다. Tomcat을 다운받아, 프로젝트의 서버로 지정해주세요.
- Tomcat의 server.xml파일을 열어 path를 "/" 으로 지정합니다.
- pom.xml에 구성된 의존성을 프로젝트에 다운받도록 합니다. (Maven 프로젝트)

<hr>

## 해당 프로젝트는 어떤 프로젝트인가요?
- 서울시 공공와이파이 데이터를 로컬 DB에 저장하여, 가장 가까운 와이파이 20개를 골라 사용자에게 보여주는 프로젝트입니다.
- 현재 위치에서 와이파이의 거리를 조회할 수 있습니다.
- 북마크 그룹을 만들고, 내가 원하는 와이파이를 해당 북마크 그룹에 추가할 수 있습니다.
- 또한, 조회 했던 위치를 기록합니다. 이를 통해 언제 위치를 조회해서 와이파이를 찾았는지 알 수 있습니다.


## 사용하는 방법을 알고싶어요!
- 프로젝트를 톰캣에 올려 가동하면, 먼저 "OpenAPI"를 클릭하여 서울시에서 와이파이 데이터를 가져옵니다.
- 내 위치를 가져오고, 근처 WIFI를 조회하면, 현재 위치에서 가까운 와이파이 20개를 조회할 수 있습니다.
- 와이파이명을 클릭하면, 해당 와이파이를 상세히 볼 수 있고, 북마크에 추가도 가능합니다.
- 북마크에서는, 북마크 그룹을 만들 수 있습니다. 순서를 지정하여 보여지는 우선순위를 지정할 수 있습니다.
- 히스토리를 누르면, 내가 위치를 조회했던 시점 기록을 조회하고 삭제할 수 있습니다.


## 사용된 기술 스택

<table>
  <tr>
    <th style="text-align: center;">Back-end </th>
    <th style="text-align: center;">Database</th>
    <th style="text-align: center;">Dependencies</th>
    <th style="text-align: center;">Front-end</th>
  </tr>
  <tr>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/-JSP-orange"></td>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/SQLite-003B57?style=flat&logo=SQLite&logoColor=white"/></td>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/-Gson-brightgreen"></td>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white"/></td>
  </tr>
  <tr>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/-Servlet-orange"></td>
    <td style="text-align: center;"></td>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/-Okhttp-15a38b"></td>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white"/></td>
  </tr>
   <tr>
    <td style="text-align: center;"></td>
    <td style="text-align: center;"></td>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/-SQLiteJDBC-blue"></td>
    <td style="text-align: center;"><img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat&logo=javascript&logoColor=black"/></td>
  </tr>
</table>

<hr>

## 사용 화면

![image](https://github.com/J-HyeonSeo/Find-Wifi-In-Seoul/assets/47245112/7a60785c-f2b2-467d-957f-570713ad4d77)
![image](https://github.com/J-HyeonSeo/Find-Wifi-In-Seoul/assets/47245112/09e8a7a1-a743-4308-b1bc-6683c67df90b)
![image](https://github.com/J-HyeonSeo/Find-Wifi-In-Seoul/assets/47245112/39561230-41dc-4d7e-a839-43a89445883a)
![image](https://github.com/J-HyeonSeo/Find-Wifi-In-Seoul/assets/47245112/0a10e658-92bc-4fe3-94ff-c520e244dec0)

<hr>

### Copyrights

본 프로젝트는 서울시 열린데이터 광장의 데이터를 사용하여 와이파이의 위치를 표시합니다. 

(서울시 열린데이터광장(서울시 공공와이파이)=> https://data.seoul.go.kr/dataList/OA-20883/S/1/datasetView.do)
