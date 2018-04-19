package com.uca.firebasetest;

import android.content.Intent;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRScanner
{
    private IntentIntegrator qrScanner;

    public QRScanner() { qrScanner.initiateScan(); }

    // Get scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // QR Scanner result holder
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        // Result is found
        if (result != null)
        {
            // If result is empty
            if (result.getContents() == null || result.getContents().isEmpty()) {
                Toast.makeText(this, "Código vacío", Toast.LENGTH_LONG).show();
            } else { // If result isn't empty
                // Add result to textview
                resultText.append(result.getContents() + "\n");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            // Show scanner
            case R.id.buttonScan:
                qrScan.initiateScan();
                break;

            // Share codes
            case R.id.buttonSend:
                // Init intent
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                // Subject
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Codigos VLC " + getDate());

                // Body
                String shareBody = readFile();
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(sharingIntent, "Enviar con.."));
                break;

            // Clear all codes from screen and file
            case R.id.buttonClear:
                // Delete file
                f.delete();

                // Create new empty file
                f = new File(getFilesDir().getPath() + "/codigos.txt");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Clear text view
                resultText.setText("");
                break;

            default:
                break;
        }
    }

    // Write content to file
    public void writeToFile(String content)
    {
        try {
            // Init file output
            FileOutputStream oFile = new FileOutputStream(f, true);

            // Write content bytes to file and add new line
            oFile.write(content.getBytes());
            oFile.write("\n".getBytes());

            // Close file
            oFile.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "No se ha podido guardar el código", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "No se ha podido guardar el código", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // Read file and return string
    public String readFile()
    {
        // Init StringBuilder
        StringBuilder str = new StringBuilder();

        // Declare line var outside loop for optimizing
        String line;
        try {
            // Init Buffered Reader
            BufferedReader br = new BufferedReader(new FileReader(f));

            // Loop through lines and append
            while ((line = br.readLine()) != null)
            {
                str.append(line);
                str.append("\n");
            }

            // Close Buffered Reader
            br.close();

            // Return StringBuilder in single String format
            return str.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se puede leer el archivo", Toast.LENGTH_LONG).show();
        }

        return "";
    }

    // Get current date in format
    public java.lang.String getDate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }
}