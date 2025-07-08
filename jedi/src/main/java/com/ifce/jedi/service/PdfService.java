package com.ifce.jedi.service;

import com.ifce.jedi.dto.PreInscricao.PreInscricaoDadosDto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    public byte[] gerarRelatorioPreInscricoes(List<PreInscricaoDadosDto> dados) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Relatório de Pré-Inscrições", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Tabela
            PdfPTable table = new PdfPTable(7); // agora 7 colunas
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3f, 4f, 3f, 3f, 3f, 2f, 2f});

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            addCell(table, "Nome Completo", headerFont);
            addCell(table, "Email", headerFont);
            addCell(table, "Celular", headerFont);
            addCell(table, "Nascimento", headerFont);
            addCell(table, "Município", headerFont);
            addCell(table, "Status", headerFont);
            addCell(table, "Aceitou Termos", headerFont);

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (PreInscricaoDadosDto dto : dados) {
                addCell(table, dto.completeName(), dataFont);
                addCell(table, dto.email(), dataFont);
                addCell(table, dto.cellPhone(), dataFont);
                addCell(table, dto.birthDate() != null ? dto.birthDate().format(formatter) : "-", dataFont);
                addCell(table, dto.municipality(), dataFont);
                addCell(table, dto.status() != null ? dto.status().name() : "-", dataFont);
                addCell(table, dto.acceptedTerms() != null && dto.acceptedTerms() ? "Sim" : "Não", dataFont);
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }

    private void addCell(PdfPTable table, String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content != null ? content : "-", font));
        cell.setPadding(5);
        table.addCell(cell);
    }
}
