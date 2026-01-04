const fs = require('fs');
const path = require('path');

const targetDir = path.join(__dirname, 'src/api/generated');

function addTsNoCheckToFile(filePath) {
  const content = fs.readFileSync(filePath, 'utf8');
  if (!content.startsWith('// @ts-nocheck')) {
    fs.writeFileSync(filePath, `// @ts-nocheck\n${content}`);
    console.log(`Prepended // @ts-nocheck to ${filePath}`);
  }
}

function processDir(dir) {
  fs.readdirSync(dir).forEach(file => {
    const fullPath = path.join(dir, file);
    if (fs.statSync(fullPath).isDirectory()) {
      processDir(fullPath);
    } else if (fullPath.endsWith('.ts')) {
      addTsNoCheckToFile(fullPath);
    }
  });
}

processDir(targetDir);
