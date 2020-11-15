package com;

import java.io.*;
import java.util.Scanner;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/10/16 16:03
 */
public class test {
    public static void main(String[] args) throws IOException {
//        File oldf=new File("C:\\Users\\mohuangNPC\\Desktop\\test");
//        File newf=new File("F:\\muce2\\Triangle\\Triangle.java");
        Scanner scanner = new Scanner(System.in);
        String result = "";
        System.out.println("原文件路径");
        String source = scanner.nextLine();
        System.out.println("目标文件路径");
        String tar = scanner.nextLine();
        System.out.println("文件前缀名字");
        String name = scanner.nextLine();
        System.out.println("文件后缀名字");
        String end = scanner.nextLine();
        System.out.println("循环次数");
        Integer count = scanner.nextInt();
        File sf = new File(source);
        for (int i = 1; i <= count; i++) {
            File tf = new File(tar+"\\"+name+"_"+i+"."+end);
            copyfile(sf,tf);
        }
    }
    public static void copyfile(File oldfile,File newfile) throws IOException {
        FileInputStream ins = new FileInputStream(oldfile);
        FileOutputStream out = new FileOutputStream(newfile);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, b.length);
        }
        ins.close();
        out.close();

        System.out.println("copy success");
    }
    public static void exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
