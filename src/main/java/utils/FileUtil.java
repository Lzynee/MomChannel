/**
 * 파일 업로드, 다운로드 및 삭제를 위한 유틸리티 메서드
 * */

package utils;

import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
    // 파일 업로드
    public static String uploadFile(HttpServletRequest req, String sDirectory)
        throws ServletException, IOException {

        Part part = req.getPart("ofile");  // request 내장 객체의 getPart() 메서드로 file 타입으로 전송된 폼값을 받아 Part 객체에 저장한다.
        String partHeader = part.getHeader("content-disposition");  // Part 객체에서 헤더값을 읽어온다.

        // 헤더의 내용에서 파일명을 추출한다.
        String[] phArr = partHeader.split("filename=");
        String originalFileName = phArr[1].trim().replace("\"", "");  // 더블쿼테이션을 제거

        // 파일명이 빈 값이 아니라면 디렉터리에 파일을 저장한다.
        if (!originalFileName.isEmpty()) {
            part.write(sDirectory + File.separator +originalFileName);
        }

        // 저장된 원본파일명을 반환한다.
        return originalFileName;
    }  // uploadFile()

    // 파일명 변경
    public static String renameFile(String sDirectory, String fileName) {
        // 새로운 파일명을 생성한다.
        String ext = fileName.substring(fileName.lastIndexOf("."));  // 원본파일명에서 확장자를 잘라낸다.
        String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());  // 생성할 문자열의 형식 : "현재날짜_시간"
        String newFileName = now + ext;  // 확장자와 파일명을 연결하여 새로운 파일명을 생성

        // 원본파일과 새로운파일에 대한 File객체를 생성한 후 파일명을 변경한다.
=======

public class FileUtil {

    //파일 업로드
    public static String uploadFile(HttpServletRequest req, String sDirectory)
            throws ServletException, IOException {
        Path saveDirectoryPath = Paths.get(sDirectory);
        Files.createDirectories(saveDirectoryPath);

        System.out.println("aaaaaaaaaaaaaa");

        //Part 객체를 통해 서버로 전송된 파일명 읽어오기
        Part part = req.getPart("memImg");

        System.out.println("bbbbbbbbbbbbb");

        //Part 객체의 헤더값 중 content-disposition 읽어오기
        String partHeader = part.getHeader("content-disposition");
        //출력결과 => form-data; name="attachedFile"; filename="파일명.jpg"
        System.out.println("partHeader="+ partHeader);

        //헤더값에서 파일명 잘라내기
        String[] phArr = partHeader.split("filename=");

        System.out.println("ccccccccc");

        String originalFileName = phArr[1].trim().replace("\"", "");

        System.out.println("dddddddd");


        //전송된 파일이 있다면 디렉토리에 저장
        if (!originalFileName.isEmpty()) {
            part.write(sDirectory+ File.separator +originalFileName);
        }

        System.out.println("eeeeeeeeeee");

        //원본 파일명 반환
        return originalFileName;
    }

    //파일명 변경
    public static String renameFile(String sDirectory, String fileName) {
        //원본파일의 확장자 잘라내기
        String ext = fileName.substring(fileName.lastIndexOf("."));
        //날짜 및 시간을 통해 파일명 생성
        String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
        //"날짜_시간.확장자" 형태의 새로운 파일명 생성
        String newFileName = now + ext;

        //기존 파일명을 새로운 파일명으로 변경
        File oldFile = new File(sDirectory + File.separator + fileName);
        File newFile = new File(sDirectory + File.separator + newFileName);
        oldFile.renameTo(newFile);

        // 변경된 파일명을 반환한다.
        return newFileName;
    }  // renameFile()
    

    //파일 다운로드
    public static void download(HttpServletRequest req, HttpServletResponse resp,
                                String directory, String sfileName, String ofileName) {
        String sDirectory = req.getServletContext().getRealPath(directory);

        try {
            // 파일을 찾아 입력 스트림 생성
            File file = new File(sDirectory, sfileName);
            InputStream iStream = new FileInputStream(file);

            // 한글 파일명 깨짐 방지
            String client = req.getHeader("User-Agent");
            if (client.indexOf("WOW64") == -1) {
                ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            else {
                ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8859-1");
            }

            // 파일 다운로드용 응답 헤더 설정
            resp.reset();
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition",
                    "attachment; filename=\"" + ofileName + "\"");
            resp.setHeader("Content-Length", "" + file.length() );

            // response 내장 객체로부터 새로운 출력 스트림 생성
            OutputStream oStream = resp.getOutputStream();

            // 출력 스트림에 파일 내용 출력
            byte b[] = new byte[(int) file.length()];

            int readBuffer = 0;

            while ( (readBuffer = iStream.read(b)) > 0 ) {
                oStream.write(b, 0, readBuffer);
            }

            // 입/출력 스트림 닫기
            iStream.close();
            oStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("예외가 발생하였습니다.");
            e.printStackTrace();

        }  // try ~ catch
    }  // download()   

    //파일 삭제
    public static void deleteFile(HttpServletRequest req,
                                  String directory, String filename) {
        String sDirectory = req.getServletContext().getRealPath(directory);
        File file = new File(sDirectory + File.separator + filename);

        if (file.exists()) {
            file.delete();
        }
    }

}
