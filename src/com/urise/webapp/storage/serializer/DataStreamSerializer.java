package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writePart(r.getContacts().entrySet(), dos, contactTypeStringEntry -> {
                dos.writeUTF(contactTypeStringEntry.getKey().name());
                dos.writeUTF(contactTypeStringEntry.getValue());
            });

            writePart(r.getSections().entrySet(), dos, entry -> {
                SectionType type = entry.getKey();
                dos.writeUTF(type.name());
                Section section = entry.getValue();
                switch (type) {
                    case PERSONAL, OBJECTIVE -> writeTextSection(dos, (TextSection) section);
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(dos, (ListSection) section);
                    case EXPERIENCE, EDUCATION -> writeOrganizationSection(dos, (OrganizationSection) section);
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readPart(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readPart(dis, () -> addSection(resume, dis));
            return resume;
        }
    }

    private interface Writer<T> {
        void writePart(T t) throws IOException;
    }

    private <T> void writePart(Collection<T> collection, DataOutputStream dos, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.writePart(item);
        }
    }

    private void writeTextSection(DataOutputStream dos, TextSection textSection) throws IOException {
        dos.writeUTF(textSection.getDescription());
    }

    private void writeListSection(DataOutputStream dos, ListSection listSection) throws IOException {
        writePart(listSection.getItems(), dos, dos::writeUTF);
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection organizationSection) throws IOException {
        writePart(organizationSection.getOrganizations(), dos, organization -> {
            dos.writeUTF(organization.getWebsite().getName());
            String url = organization.getWebsite().getUrl();
            dos.writeUTF((url != null) ? url : "");
            writePeriod(dos, organization.getPeriod());
        });
    }

    private void writePeriod(DataOutputStream dos, List<Period> periods) throws IOException {
        writePart(periods, dos, period -> {
            writeLocalDate(dos, period.getDateFrom());
            writeLocalDate(dos, period.getDateTo());
            dos.writeUTF(period.getPosition());
            dos.writeUTF(period.getDescription());
        });
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
    }

    private interface Reader {
        void readPart() throws IOException;
    }

    private void readPart(DataInputStream dis, Reader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.readPart();
        }
    }

    private void addSection(Resume r, DataInputStream dis) throws IOException {
        SectionType st = SectionType.valueOf(dis.readUTF());
        switch (st) {
            case PERSONAL, OBJECTIVE -> addTextSection(r, dis, st);
            case ACHIEVEMENT, QUALIFICATIONS -> r.addSection(st, new ListSection(addSection(dis, dis::readUTF)));
            case EXPERIENCE, EDUCATION -> r.addSection(st, new OrganizationSection(
                    addSection(dis, () -> new Organization(new Link(dis.readUTF(), dis.readUTF()),
                            addSection(dis, () -> new Period(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()))))
            ));
        }
    }

    private void addTextSection(Resume r, DataInputStream dis, SectionType st) throws IOException {
        r.addSection(st, new TextSection(dis.readUTF()));
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private <T> List<T> addSection(DataInputStream dis, ElementReader<T> reader) throws IOException {
        List<T> items = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            items.add(reader.read());
        }
        return items;
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
}
