package com.stockmarket.instruments;

import com.stockmarket.util.MySqlClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrumentService
{

    Connection connection = MySqlClient.getConnection();

    public Instrument getInstrumentById(int instrumentId) throws Exception
    {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM instruments WHERE instrument_id = ?");
        st.setInt(1, instrumentId);
        ResultSet rs = st.executeQuery();

        if (!rs.isBeforeFirst()) {
            throw new Exception("Unable to find the instrument with the given Instrument ID");
        }

        Instrument instrument = new Instrument();
        while (rs.next())
        {
            instrument.setInstrumentId(rs.getInt("instrument_id"));
            instrument.setInstrumentName(rs.getString("instrument_name"));
            instrument.setLtp(rs.getFloat("ltp"));
            instrument.setType(rs.getString("type"));
            instrument.setLtpChange(rs.getFloat("ltpchange"));
        }

        return instrument;
    }


    public List<Instrument> getAllInstruments() throws SQLException
    {

        List<Instrument> instruments = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM instruments");

        while (rs.next())
        {
            Instrument instrument = new Instrument();
            instrument.setInstrumentId(rs.getInt("instrument_id"));
            instrument.setInstrumentName(rs.getString("instrument_name"));
            instrument.setLtp(rs.getFloat("ltp"));
            instrument.setType(rs.getString("type"));
            instrument.setLtpChange(rs.getFloat("ltpchange"));
            instruments.add(instrument);
        }
        return instruments;
    }



    public boolean instrumentExistsWithName(String instrumentName) throws Exception {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM instruments WHERE instrument_name = ?");
        st.setString(1, instrumentName);
        ResultSet rs = st.executeQuery();

        if (rs.isBeforeFirst()) {
            throw new Exception("Instrument with the given name already exists");
        }

        return false;
    }


    public String createInstrument(Instrument instrument) throws SQLException
    {
        PreparedStatement st = connection.prepareStatement("INSERT INTO instruments(instrument_name, ltp, type, ltpchange) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        st.setString(1, instrument.getInstrumentName());
        st.setFloat(2, instrument.getLtp());
        st.setString(3, instrument.getType());
        st.setFloat(4, instrument.getLtpChange());

        int affectedRows  = st.executeUpdate();
        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        int insertedId = rs.getInt(1);
        return "New Instrument has been created with a Instrument ID " + insertedId;
    }


    public String updateInstrument(Instrument reqInstrument, Instrument instrument) throws SQLException {
        PreparedStatement st = connection.prepareStatement("UPDATE instruments SET instrument_name=?, ltp=?, type=?, ltpchange=? WHERE instrument_id=?");
//        [instrument.instrument_name, instrument.ltp, instrument.type, instrument.ltp - reqInstrument.ltp, reqInstrument.instrument_id]
        st.setString(1, instrument.getInstrumentName());
        st.setFloat(2, instrument.getLtp());
        st.setString(3, instrument.getType());
        st.setFloat(4, reqInstrument.getLtp() - instrument.getLtp());
        st.setInt(5, reqInstrument.getInstrumentId());

        int affectedRows = st.executeUpdate();
        return "Instrument UPDATED";
    }

    public String deleteInstrument(Instrument instrument) throws SQLException
    {
        PreparedStatement st = connection.prepareStatement("DELETE FROM instruments WHERE instrument_id = ?");
        st.setInt(1, instrument.getInstrumentId());
        int affectedRows = st.executeUpdate();
        return "Instrument DELETED";
    }


//    TODO: Buy instrument and sell Instrument

}
