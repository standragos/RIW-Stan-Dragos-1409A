
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class QuoteClient {
    public static void main(String[] args) throws IOException {

        byte [] mesaj = new byte[31];
        byte [] response = new byte[512];
        Arrays.fill(mesaj,(byte)0);
        Random x = new Random();
        mesaj[1] = (byte)(0xff & (x.nextInt(255)+1));
        mesaj[5] = (byte)(1);
        DatagramSocket socket = new DatagramSocket();
        String adresa = "www.tuiasi.ro";
        String [] labels = adresa.split("\\.");
        int idx = 12;
        for (int i=0; i<labels.length; ++i){
            int tmp = labels[i].length();
            mesaj[idx++] = (byte) (tmp & 0xFF);
            for(tmp=0; tmp<labels[i].length();++tmp){
                mesaj[idx++] = (byte) labels[i].charAt(tmp);
            }
        }
        mesaj[idx] = 0;
        mesaj[30] = mesaj[28] = (byte)((0x01));


        InetAddress address = InetAddress.getByName("81.180.223.1");
        DatagramPacket packet = new DatagramPacket(mesaj, mesaj.length, address, 53);
        socket.send(packet);

        // get response
        packet = new DatagramPacket(response, 512);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);

        socket.close();
    }
}