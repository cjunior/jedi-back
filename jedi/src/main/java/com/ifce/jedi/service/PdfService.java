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
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{
                    3.5f, 2.5f, 4.5f, 2.5f, 2.5f, 2.5f, 2.5f, 2.5f, 2.5f
            });

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            // Cabeçalhos
            addCell(table, "Nome Completo", headerFont, false);
            addCell(table, "CPF", headerFont, true);
            addCell(table, "Email", headerFont, false);
            addCell(table, "Celular", headerFont, true);
            addCell(table, "Nascimento", headerFont, true);
            addCell(table, "Município", headerFont, false);
            addCell(table, "Outros Municípios", headerFont, false);
            addCell(table, "Status", headerFont, true);
            addCell(table, "Termos", headerFont, true);

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Dados
            for (PreInscricaoDadosDto dto : dados) {
                addCell(table, dto.completeName(), dataFont, false);
                addCell(table, dto.cpf(), dataFont, true);
                addCell(table, dto.email(), dataFont, false);
                addCell(table, dto.cellPhone(), dataFont, true);
                addCell(table, dto.birthDate() != null ? dto.birthDate().format(formatter) : "-", dataFont, true);
                addCell(table, dto.municipality(), dataFont, false);

                String otherMunicipalityValue = dto.otherMunicipality() != null && !dto.otherMunicipality().isBlank()
                        ? dto.otherMunicipality()
                        : "-";
                addCell(table, otherMunicipalityValue, dataFont, false);

                addCell(table, dto.status() != null ? dto.status().name() : "-", dataFont, true);
                addCell(table, dto.acceptedTerms() != null && dto.acceptedTerms() ? "Sim" : "Não", dataFont, true);
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }

    private void addCell(PdfPTable table, String content, Font font, boolean noWrap) {
        PdfPCell cell = new PdfPCell(new Phrase(content != null ? content : "-", font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        if (noWrap) {
            cell.setNoWrap(true);
        }

        table.addCell(cell);
    }
}
