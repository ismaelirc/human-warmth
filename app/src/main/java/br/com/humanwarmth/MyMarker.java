package br.com.humanwarmth;

/**
 * Created by ismael on 10/04/16.
 */
public class MyMarker
{
    private String mLabelDescricao;
    private String mLabelEndereco;
    private String mLabelResponsavel;
    private Double mLatitude;
    private Double mLongitude;

    public MyMarker(String label,String endereco,String responsavel, Double latitude, Double longitude)
    {
        this.mLabelDescricao = label;
        this.mLabelEndereco = endereco;
        this.mLabelResponsavel = responsavel;
        this.mLatitude = latitude;
        this.mLongitude = longitude;

    }

    public String getmLabelDescricao()
    {
        return mLabelDescricao;
    }

    public void setmLabelDescricao(String mLabel)
    {
        this.mLabelDescricao = mLabel;
    }

    public String getmLabelEndereco()
    {
        return mLabelEndereco;
    }

    public void setmLabelEndereco(String mLabel)
    {
        this.mLabelEndereco = mLabel;
    }

    public String getmLabelResponsavel()
    {
        return mLabelResponsavel;
    }

    public void setmLabelResponsavel(String mLabel)
    {
        this.mLabelResponsavel = mLabel;
    }

    public Double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude()
    {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
}
