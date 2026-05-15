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
          <p>Phase 5: GPS & Location Complete.</p>
          <p>Features: FusedLocationProviderClient, High-accuracy GPS, Google Maps Preview, Permission Handling.</p>
          <p>Architecture: Domain-driven LocationTracker interface, Repository implementation.</p>
          <p>Status: Location layer ready. Next: Report Submission form setup.</p>
        </div>
      </body>
    </html>
  `);
});

app.listen(PORT, "0.0.0.0", () => {
  console.log(\`Server running on port \${PORT}\`);
});
