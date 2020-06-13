import java.io.*;
import java.util.*;

public class GetFile {

	public GetFile(HashMap<String, ScheduleFile> schedule, HashMap<String, String> mywork) {
		// 해쉬를 이용한 빠른 접근

		// 디렉토리 내 모든 txt 불러오기
		// 1. 일정파일
		File folder = new File("schedule/");
		File[] listOfFiles = folder.listFiles();

		InputStreamReader in = null;
		FileInputStream fin = null;
		BufferedReader bufReader = null;
		String[] filename;
		String day;
		for (File file : listOfFiles) {
			if (file.isFile()) {
				day = file.getName();
				filename = day.split(".txt");
				day = filename[0];
				ScheduleFile sch = new ScheduleFile();
				try {
					fin = new FileInputStream("schedule/" + file.getName());
					in = new InputStreamReader(fin);
					bufReader = new BufferedReader(in);
					String line = "";
					int count = 0;
					while ((line = bufReader.readLine()) != null) {
						switch (count) {
						case 0:
							sch.setTitle(line);
							break;
						case 1:
							sch.setArea(line);
							break;
						case 2:
							sch.setWork(line);
							break;
						case 3:
							sch.setCategory(line);
							break;
						case 4:
							sch.setWho(line);
							break;
						case 5:
							sch.setStart(line);
							break;
						case 6:
							sch.setTime(line);
							break;
						case 7:
							sch.setInfo(line);
							break;
						case 8:
							sch.setR(Integer.parseInt(line));
							break;
						case 9:
							sch.setG(Integer.parseInt(line));
							break;
						case 10:
							sch.setB(Integer.parseInt(line));

						}
						count++;
					}
					schedule.put(day, sch);
					in.close();
					fin.close();
				} catch (IOException e) {
					System.out.println("파일을 불러오는 과정에서 문제가 발생했습니다.");
					e.printStackTrace();
				}

			}
		}

		// 2. 할일 파일
		File folder2 = new File("data/");
		File[] listOfFiles2 = folder2.listFiles();

		FileInputStream fin2 = null;
		String mw = "";
		for (File file2 : listOfFiles2) {
			if (file2.isFile()) {
				try {
					fin2 = new FileInputStream("data/" + file2.getName());
					Scanner scan = new Scanner(fin2);

					while (scan.hasNextLine()) {
						mw += scan.nextLine();
					}
					String day2 = file2.getName();
					day2 = day2.substring(0, 6);
					mywork.put(day2, mw);
					fin2.close();

					//System.out.println(mywork.get("201812"));

				} catch (IOException e) {
					System.out.println("파일을 불러오는 과정에서 문제가 발생했습니다.");
					e.printStackTrace();
				}
			}
		}
	}

}
