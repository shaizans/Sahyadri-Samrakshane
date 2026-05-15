import express from "express";
import path from "path";

const app = express();
const PORT = 3000;

app.get("*", (req, res) => {
  res.send(`
    <html>
      <head>
        <title>Sahyadri-Samrakshane - Dev Status</title>
        <style>
          body { font-family: sans-serif; display: flex; align-items: center; justify-content: center; height: 100vh; background: #F5F5DC; color: #2D5A27; }
          .container { text-align: center; padding: 2rem; border: 2px solid #2D5A27; border-radius: 12px; }
        </style>
      </head>
      <body>
        <div class="container">
          <h1>Sahyadri-Samrakshane (Forest Sentinel)</h1>
          <p>Phase 10: Final Polish Complete.</p>
          <p>Highlights: Forest Branding UI, Profile Management, Offline-First Architecture, GPS Integration.</p>
          <p>App Status: Production Ready.</p>
        </div>
      </body>
    </html>
  `);
});

app.listen(PORT, "0.0.0.0", () => {
  console.log(`Server running on port ${PORT}`);
});
