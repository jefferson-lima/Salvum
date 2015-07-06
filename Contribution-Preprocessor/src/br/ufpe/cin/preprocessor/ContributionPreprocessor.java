package br.ufpe.cin.preprocessor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Scanner;

public class ContributionPreprocessor {

	private String targetPathDirectory;
	private String parentCommitHash;
	private String childCommitHash;
	private String diffFilePath;

	public ContributionPreprocessor(String sourceDirectory,
			String parentCommitHash, String childCommitHash, String diffFilePath) {
		this.targetPathDirectory = sourceDirectory;
		this.parentCommitHash = parentCommitHash;
		this.childCommitHash = childCommitHash;
		this.diffFilePath = diffFilePath;
	}
	
	public ContributionPreprocessor(Properties p, String commitHash) throws IOException {
		this.targetPathDirectory = p.getProperty("targetPathDirectory");
		this.childCommitHash = commitHash;
		this.parentCommitHash = DiffFileUtil.runParents(targetPathDirectory, commitHash);
		this.diffFilePath = p.getProperty("diffFilePath");
	}

	public void preprocess() throws IOException {

		DiffFileUtil.runDiffCommand(this.targetPathDirectory,
				this.parentCommitHash, this.childCommitHash, this.diffFilePath);
		Path targetProjPath = DiffFileUtil.loadDiffFile(this.diffFilePath);

		Scanner scanner = new Scanner(targetProjPath);

		ContextManagerContribution manager = ContextManagerContribution.getContext();

		String className = "";

		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();

			if (nextLine.startsWith(Tag.DIFF)) {
				className = formatClassName(nextLine);
				continue;
			} else if (nextLine.startsWith(Tag.LINES)) {
				String[] linesAddition = obtainLineNumbers(nextLine, "+");
				Integer lineNumber = Integer.valueOf(linesAddition[0]);
				int totalChunk = Integer.valueOf(linesAddition[1]);

				// String[] linesRemoval = obtainLineNumbers(nextLine, "-");
				// int totalChunkRemoval = Integer.valueOf(linesRemoval[1]);
				//
				// int totalChunk = (totalChunkAddition > totalChunkRemoval) ?
				// totalChunkAddition
				// : totalChunkRemoval;

				int i = 0;

				while (i < totalChunk && scanner.hasNextLine()) {
					String posLine = scanner.nextLine();
					if (posLine.startsWith("+")) {
						// adicionar linha
						manager.addClassLinesInfo(className, lineNumber);
					}
					if (posLine.startsWith("-")) {
						lineNumber--;
						totalChunk++;
					}
					lineNumber++;
					i++;
				}
			}
		}

		System.out.println(manager.getMapClassesLineNumbers().toString());

	//	DiffFileUtil.deleteDiffFile(targetProjPath);
	}

	private String formatClassName(String nextLine) {
		String className;
		// significa que comeca o diff de um novo arquivo
		className = nextLine.substring(nextLine.indexOf("/src"),
				nextLine.indexOf("."));
		className = className.replace("/", ".");
		className = className.replace(".src.", "");
		return className;
	}

	private String[] obtainLineNumbers(String nextLine, String signal) {
		int indexOf;
		if (signal.equals("+")) {
			indexOf = nextLine.indexOf(" " + Tag.LINES);
		} else {
			indexOf = nextLine.indexOf(" +");
		}
		String substring = nextLine.substring(nextLine.indexOf(signal) + 1,
				indexOf);
		String[] lines = substring.split(",");
		return lines;
	}

	/**
	 * 
	 * @param args
	 *            args[0] is the target project path; args[1] is the parent
	 *            commit hash; args[2] is the child commit hash; args[3] is the
	 *            diff file path
	 */
	public static void main(String[] args) {
		// test purposes with gitblit
		String projectPath = "/Users/rodrigoandrade/Documents/workspaces/Doutorado/opensource/gitblit/";
		String parentCommit = "2365822625a0a46b2d25f83b698801cd18e811c0";
		String childCommit = "efdb2b3d0c6f03a9aac9e65892cbc8ff755f246f";
		String diffFile = "/Users/rodrigoandrade/Documents/workspaces/Doutorado/joana/Contribution-Preprocessor/diffFiles/diff.txt";

		try {
			// test purposes with gitblit
			ContributionPreprocessor cp = new ContributionPreprocessor(
					projectPath, parentCommit, childCommit, diffFile);
			cp.preprocess();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
